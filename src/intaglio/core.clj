(ns intaglio.core
  (:require [net.cgrand.enlive-html :as html]
             [cheshire.core :refer :all]
             [markdown.core :as md])
  (:import
   (java.util Date UUID)))

(use 'clojure.pprint)


(defn write-config []
  (spit "config.json" (generate-string @config-atom))
  (log-config))

(def test-post (parse-string (slurp "testparse.json" ) true))

(def config-atom (atom (parse-string (slurp "config.json") true)))

(defn inc-post []
  (swap! config-atom update-in [:total-posts]  inc))

(defn log-config []
  (spit "config-log.json" (generate-string @config-atom) :append true))

(defn add-post [post]
  (inc-post)
  (def file-name (str (@config-atom :directory) "/entry-" (@config-atom :total-posts) ".md"))
  (spit file-name (post :markdown))
  (write-config)
  file-name)

(defn format-post [raw-post]
  (dissoc (assoc raw-post
            :file (add-post raw-post)
            :date (Date. )
            :timestamp (System/currentTimeMillis)
            :uuid (str (java.util.UUID/randomUUID))
            :edited (Date. )) :markdown))


(defn append-posts-file [formatted-post]
 (spit (@config-atom :posts-file)
       (generate-string {:entry-num (@config-atom :total-posts)
                         :entry formatted-post} {:date-format "MM-dd-yyyy-H-m"})))


(append-posts-file (format-post test-post))
  
(html/deftemplate main-template "templates/skeleton.html"
  []
   [:id#master] (html/content (:markdown make-config-map)))

(html/defsnippet post-snippet "templates/post.html"
  [:div.row]
  [post-md]
  [:div] (html/content post-md ))



(pprint (post-snippet (slurp "posts/somepost.html")))

(def postys (html/html-resource "templates/post.html"))
(pprint postys)
