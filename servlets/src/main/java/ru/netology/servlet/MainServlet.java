package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MainServlet extends HttpServlet {
    private PostController controller;
    private ConcurrentHashMap<String, Map<String, Handler>> handlers;
    private static final String PATH = "/api/posts";
    private static final String PATH_WITH_PARAMS = "/api/posts/\\d+";

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);

        handlers = new ConcurrentHashMap<>();

        // primitive routing
        addHandler("GET", PATH, (req, resp, path) -> {
            controller.all(resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        });
        addHandler("POST", PATH, (req, resp, path) -> {
            controller.save(req.getReader(), resp);
            resp.setStatus(HttpServletResponse.SC_OK);
        });

        addHandler("GET", PATH_WITH_PARAMS, (req, resp, path) -> {
            try {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.getById(id, resp);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (NotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        });
        addHandler("DELETE", PATH_WITH_PARAMS, (req, resp, path) -> {
            try {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
                controller.removeById(id, resp);
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (NotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        });
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            var handler = handlers.get(method).get(path);
            handler.handle(req, resp, path);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void addHandler(String method, String path, Handler handler) {
        if (!handlers.containsKey(method)) {
            handlers.put(method, new HashMap<>());
        }
        handlers.get(method).put(path, handler);
    }
}

