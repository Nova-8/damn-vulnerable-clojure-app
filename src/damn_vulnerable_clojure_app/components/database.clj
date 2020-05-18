(ns damn-vulnerable-clojure-app.components.database
  (:require [com.stuartsierra.component :as component]))


(defrecord Database [connection]
  component/Lifecycle

  (start [this]
    (assoc this :database {}))

  (stop [this]
    (.close connection)
    (assoc this :database nil)))

(defn new-database [] (map->Database {}))
