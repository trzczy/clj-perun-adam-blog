(set-env!
  :source-paths #{"src"}
  :resource-paths #{"resources"}
  :dependencies '[[perun "0.4.3-SNAPSHOT" :scope "test"]
                  [hiccup "1.0.5" :exclusions [org.clojure/clojure]]
                  [clj-time "0.14.0"]
                  [px0/beautify-web "0.1.1"]                  
                  [hashobject/boot-s3 "0.1.3-SNAPSHOT"]
                  [pandeiro/boot-http "0.8.3" :exclusions [org.clojure/clojure]]])

(require '[clojure.string :as str]
         '[io.perun :as perun]
         '[io.perun.example.views.index :as index-view]
         '[io.perun.example.views.post :as post-view]
         '[hashobject.boot-s3 :refer :all]
         '[pandeiro.boot-http :refer [serve]])

(deftask build-classic
  "Build test blog. This task is just for testing different plugins together."
  []
  (let [is-of-type? (fn [{:keys [permalink]} doc-type]
;; (.startsWith permalink (str "/" doc-type))
 (re-matches (re-pattern (str ".*" doc-type ".*")) permalink)


)]


  (comp
        (perun/global-metadata)
        (perun/markdown)
;;        (perun/draft)
;;        (perun/print-meta)
        (perun/slug)
;;        (perun/ttr)
;;        (perun/word-count)
        (perun/build-date)
;;        (perun/gravatar :source-key :author-email :target-key :author-gravatar)
;;;;;        (perun/render :renderer 'io.perun.example.views.contact/render)
        (perun/render :renderer 'io.perun.example.views.about/render :filterer #(is-of-type? % "about"))
        (perun/render :renderer 'io.perun.example.views.contact/render :filterer #(is-of-type? % "contact"))
;1;        (perun/render :renderer 'io.perun.example.views.about/render :filterer #(is-of-type? % "(contact|about)"))
        (perun/render :renderer 'io.perun.example.views.post/render :filterer #(is-of-type? % "post"))

        (perun/collection :renderer 'io.perun.example.views.index/render :page "index.html" :filterer #(is-of-type? % "post"))
        (perun/collection :renderer 'io.perun.example.views.tagindex/render :page "tagindex.html")
        (perun/tags :renderer 'io.perun.example.views.tag/render)
;1;        (perun/paginate :renderer 'io.perun.example.paginate/render)
;;        (perun/static :renderer 'io.perun.example.views.xperia/render :page "xperia.html")
;;        (perun/inject-scripts :scripts #{"start.js"})
;;        (perun/sitemap)
;;        (perun/rss :description "Hashobject blog")
;;        (perun/atom-feed :filterer :original)
        (perun/print-meta)
        (target)
        (notify))))

;;(deftask build
(deftask deploy
  []
  (comp
;;    (watch)
    (build-classic)
    (s3-sync
           :bucket "ireland.adam"
           :access-key (System/getenv "AWS_ACCESS_KEY")
           :secret-key (System/getenv "AWS_SECRET_KEY")
           :source "public"
           :metadata {:cache-control "max-age=315360000, no-transform, public"})
))

(deftask dev
  []
  (comp
    (watch)
    (build-classic)
    (serve :resource-root "public" :port 3003)))
