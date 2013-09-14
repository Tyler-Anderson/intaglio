(defproject intaglio "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [enlive "1.1.4"]
                 [markdown-clj "0.9.31"]
                 [net.cgrand/moustache "1.2.0-alpha2"]
                 [cheshire "5.2.0"]]
  :profiles {:dev {:plugins [[com.cemerick/austin "0.1.1"]]}})
