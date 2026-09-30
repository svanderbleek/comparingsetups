# CCSDemo

Demo project to develop proficiency with Clojure/ClojureScript

## Run

For development

```
clj -M:run                # backend
shadow-cljs watch ccsdemo # frontend
```

For production

```
clj -T:build uber         # build
```

## Todo

* use re-frame in client
* Connect client to server
* Connect server to Postgres
* Dockerize
* Kubernetes

