(ns build
  (:require [clojure.java.process :as process]
            [clojure.tools.build.api :as b]))

(def lib 'app/app)
(def version "0.1.0")
(def class-dir "target/classes")
(def uber-file (format "target/%s-%s-standalone.jar" (name lib) version))

(defn- basis [] (b/create-basis {:project "deps.edn"}))

(defn clean [_]
  (b/delete {:path "target"})
  (b/delete {:path "resources/public/js"}))

(defn cljs
  "Compile the re-frame frontend into resources/public/js with an optimized build."
  [_]
  (let [p (process/start {:out :inherit :err :inherit}
                         "npx" "shadow-cljs" "release" "app")]
    (when-not (zero? @(process/exit-ref p))
      (throw (ex-info "shadow-cljs release failed" {})))))

(defn uber
  "Build the frontend, AOT-compile the server and package a standalone jar."
  [_]
  (clean nil)
  (cljs nil)
  (b/copy-dir {:src-dirs ["src/clj" "resources"] :target-dir class-dir})
  (b/compile-clj {:basis (basis) :ns-compile '[app.main] :class-dir class-dir})
  (b/uber {:class-dir class-dir
           :uber-file uber-file
           :basis     (basis)
           :main      'app.main})
  (println "Built" uber-file))
