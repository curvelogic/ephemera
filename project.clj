(defproject org.curvelogic/ephemera "0.1.0-SNAPSHOT"
  :description "Temp file / directory utilities for Clojure."
  :url "https://github.com/curvelogic/ephemera"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]

  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :plugins [[lein-midje "3.1.3"]]}})
