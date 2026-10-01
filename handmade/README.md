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
clj -T:build uber
docker buildx build --platform linux/amd64 -t us-central1-docker.pkg.dev/ccsdemo-510302/ccsdemo/app:v1 .
kubectl apply -f deployment.yaml
kubectl get service ccsdemo-app-service
```

go to `http://EXTERNAL-IP/index.html`

## Todo

* Fix hardcoded fetch url
* Enable https
* Connect server to Postgres
* Display data in client
