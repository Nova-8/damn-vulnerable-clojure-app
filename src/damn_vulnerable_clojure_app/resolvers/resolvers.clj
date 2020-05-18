(ns damn-vulnerable-clojure-app.resolvers.resolvers
  (:require
   [clojure.java.io :as io]
   [clojure.walk :as walk]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.walmartlabs.lacinia.util :as util]
   [com.walmartlabs.lacinia :as lacinia]
   [clojure.edn :as edn]
   [damn-vulnerable-clojure-app.schema.example :as e-s]
   [damn-vulnerable-clojure-app.resolvers.queries.example :as e-r])
  (:import (clojure.lang IPersistentMap)))

(defn resolver-map
  [component]
  (let [db (:db component)]
    {:query/example-by-id e-r/get-example-by-id}))

(defn load-schema
  [component]
  (-> (e-s/get-schema)
      (util/attach-resolvers (resolver-map component))
      schema/compile))

(def schema (load-schema {:db {}}))

(defn simplify
  "Converts all ordered maps nested within the map into standard hash maps, and
   sequences into vectors, which makes for easier constants in the tests, and eliminates ordering problems."
  [m]
  (walk/postwalk
    (fn [node]
      (cond
        (instance? IPersistentMap node)
        (into {} node)

        (seq? node)
        (vec node)

        :else
        node))
    m))

(println (-> (lacinia/execute schema "{ example_by_id(id: \"1\") {id example}}" nil nil) simplify))
