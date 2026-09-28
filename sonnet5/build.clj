(ns build
  "tools.build script for the backend server.
   Run: clojure -T:build uber
   (Build the frontend with `npm run release` first so resources/public/js
   is populated and gets bundled into the jar.)"
  (:require [clojure.tools.build.api :as b]))

(def lib 'myapp/server)
(def version "0.1.0")
(def class-dir "target/classes")
(def uber-file (format "target/%s-standalone.jar" (name lib)))
(def basis (delay (b/create-basis {:project "deps.edn"})))

(defn clean [_]
  (b/delete {:path "target"}))

(defn uber [_]
  (clean nil)
  (b/copy-dir {:src-dirs ["src/backend" "resources"]
               :target-dir class-dir})
  (b/compile-clj {:basis @basis
                   :src-dirs ["src/backend"]
                   :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis @basis
           :main 'myapp.server.core})
  (println "Built" uber-file))
