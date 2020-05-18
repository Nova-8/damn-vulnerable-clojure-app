(ns damn-vulnerable-clojure-app.resolvers.queries.example)

(def mocked-data [{:id "1" :example "example"}])

(defn get-example-by-id [_ {:keys [id]} _] (last  (filter #(= (:id %) id) mocked-data)))
