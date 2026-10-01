# CCSDemo

Demo project to develop proficiency with Clojure/ClojureScript

## Run

### Dev

```
clj -M:run                # start backend first
shadow-cljs watch ccsdemo # frontend
```

go to `localhost:3001/index.html`

### Prod

```
clj -T:build uber         # build
```

## Todo

* Connect server to Postgres
* Display data in client
* Dockerize
* Kubernetes

