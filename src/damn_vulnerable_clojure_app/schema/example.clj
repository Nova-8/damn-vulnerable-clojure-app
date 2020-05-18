(ns damn-vulnerable-clojure-app.schema.example
  (:require
   [clojure.java.io :as io]
   [clojure.edn :as edn]
   [com.stuartsierra.component :as component]))

(defn get-schema
  []
  (-> (io/resource "example-schema.edn")
      slurp
      edn/read-string))
