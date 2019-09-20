(ns io.perun.example.views.index
  (:require
   [hiccup.page :refer [html5]]
   [beautify-web.core :as bw]
   [io.perun.example.core :as core]))

  
(defn render [{meta :meta posts :entries post :entry}]
  (bw/beautify-html
   (html5 {:lang "en" :itemtype "http://schema.org/Blog"}
         (core/head meta "Home")
         [:body
;;          (core/head meta (:title post))
          ;;(core/header meta)
          (core/header meta {:active :home :logo :site})
          [:main
           {:role "main"}
           (for [{:keys [title permalink tags date-published content] :as post} posts]
             [:article
              [:div.row
               [:div.columns
                 [:time (core/date-formatted date-published)]
                 " "
                 [:a
                  {:style "color:#678; text-decoration: none;",
                   :href (:permalink post)}
                  (:title post)]
                [:p
                 (for [tag tags]
                   (list [:a
                    {:style "color:#654; text-decoration: none;",
                     :href (str tag ".html")}
                    (str "#" tag)]" "))]

]]])]

          
          (core/scripts)])))
