(ns damn-vulnerable-clojure-app.server
  (:gen-class) ; for -main method in uberjar
  (:require [damn-vulnerable-clojure-app.components :as components]
            [com.stuartsierra.component :as component]))

(defn -main
  [& args]
  (let [base (components/base)]
    (component/start base)))
