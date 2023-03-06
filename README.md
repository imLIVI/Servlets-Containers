# Servlets-Containers
## CRUD
### Description
As part of the lecture, we implemented an almost full-fledged <a href="https://github.com/netology-code/jspr-code/tree/master/04_serlvets/servlets">In-Memory CRUD server
(Create Read Update Delete)</a> based on servlets. This server is missing two things:

1. To bring the code into proper form: to make methods into constants, to remove duplicate code.
2. Implement a repository — while a stub is installed instead of the repository.
### Task
1. Refactor the code.
2. Implement the repository taking into account that repository methods can be called competitively, i.e. in different threads.

How should ```save``` work:
1. If a post comes from a client with id=0, it means that this is the creation of a new post. You save it in the list and assign it a new id. It is enough to store a counter with an integer and increase it by 1 when creating each new post.
2. If a post comes from a client with id !=0, it means that it is saving (updating) an existing post. You search for it in the list by id and update it. Think about yourself what you will do if there is no post with such an id: there may be different strategies here.

<a href="https://github.com/netology-code/jspr-homeworks/tree/master/04_servlets">(RUS version of description)</a>

### Results
<a href="https://www.postman.com/downloads/">Postman</a> was used to test the server:
1. Sent 4 POST request:

![post](https://user-images.githubusercontent.com/63547457/223095408-f12c0ebc-de05-4b42-9644-8477308ed653.png)

2. Sent GET request:

![get](https://user-images.githubusercontent.com/63547457/223095540-abe76401-9be4-4126-9121-207808389d80.png)

3. Sent DELETE request:

![delete](https://user-images.githubusercontent.com/63547457/223095625-3b8b1098-8736-4a0b-8aea-e3cb83dde59a.png)

4. Sent GET request again:

![get2](https://user-images.githubusercontent.com/63547457/223095679-3e99e450-7b77-4887-ae22-6f81efe36554.png)
