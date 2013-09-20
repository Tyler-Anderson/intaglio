(ns intaglio.core
  (:require [net.cgrand.enlive-html :as html]
             [cheshire.core :refer :all]
             [markdown.core :as md])
  (:import
   (java.util Date UUID)
   (java.io StringReader)))

(use 'clojure.pprint)


(def config-atom (atom (parse-string (slurp "config.json") true)))

(defn log-config []
  (spit "config-log.json" (generate-string @config-atom) :append true))

(defn write-config []
  (spit "config.json" (generate-string @config-atom))
  (log-config))

(def test-post (parse-string (slurp "testparse.json" ) true))

(defn inc-post []
  (swap! config-atom update-in [:total-posts]  inc))

(defn write-post [post]
  (inc-post)
  (def file-name (str (@config-atom :directory) "/entry-" (@config-atom :total-posts) ".md"))
  (spit file-name (post :markdown))
  (write-config)
  file-name)

(defn format-post [post]
  (dissoc (assoc post
            :file (write-post post)
            :date (Date. )
            :timestamp (System/currentTimeMillis)
            :uuid (str (java.util.UUID/randomUUID))
            :edited (Date. )) :markdown))


(defn append-posts-file [formatted-post]
  (spit (@config-atom :posts-file)
    (generate-string
      (conj (parse-string (slurp (@config-atom :posts-file)))
            {:entry-num (@config-atom :total-posts)
             :entry formatted-post}) {:date-format "MM-dd-yyyy-H-m"})))


(defn post-html [{file :file}]
  ((fn [html-string] (html/html-resource (StringReader. html-string)))
  (md/md-to-html-string (slurp file))))

(post-html {:file "posts/entry-9.md"})

(pprint (conj (post-html {:file "posts/entry-9.md"}) (post-html {:file "posts/entry-9.md"})))

(def p-col (partition-all (@config-atom :per-page)
                       (reverse (sort-by :entry-num (parse-string (slurp "posts.json") true)))))

(append-posts-file (format-post test-post))
  
(html/deftemplate main-template "templates/skeleton.html"
  []
   [:id#master] (html/content ()))

(pprint (conj (post-snippet (md/md-to-html-string (slurp "posts/entry-41.md"))) (post-snippet (md/md-to-html-string (slurp "posts/entry-41.md")))))

(html/defsnippet post-snippet "templates/post.html"
  [:div.row]
  [post]
  [:div] (html/content (html-enlive post)))

(gather-posts [(gather-posts [(gather-posts [(gather-posts [(gather-posts [(gather-posts [(gather-posts [(gather-posts [(gather-posts [
  
(defn make-page [page-num posts-map]
  (main-template page-num

(defn html-enlive 
  [html-string]
    (html/html-resource (StringReader. html-string)))

(defn write-html [{:keys [output-dir file-name posts]}]
  (spit (str output-dir "/" file-name)
        (apply str (html/emit* (main-template posts)))))

(spit "test.html"  (apply str (html/emit* (post-snippet (slurp "posts/somepost.html")))))

(def postys (html/html-resource "templates/post.html"))
(pprint postys)
