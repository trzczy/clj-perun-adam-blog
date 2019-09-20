(ns io.perun.example.views.tag
  (:require
   [hiccup.page :refer [html5]]
   [beautify-web.core :as bw]
   [io.perun.example.core :as core]))

(defn render [{meta :meta posts :entries entry :entry}]
  (bw/beautify-html
  (html5 {:lang "en" :itemtype "http://schema.org/Blog"}
         (core/head meta (:tag entry))
         [:body
          (core/header meta {:site-title (:tag entry) :h1-menu-item :out-of-menu :active :out-of-menu :logo :site})
          [:main
           {:role "main"}
           [:article
            [:div.row
             [:div.columns
              [:h1 (str "Articles of a tag: " (:tag entry))]]]]
;              [:p
;               [:pre {:style "white-space: pre-wrap;"} (str meta)]]]]]
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
                   (list [:a {
                              :style "color:#654; text-decoration: none;"
                              :href (str tag ".html")}
                          (str "#" tag)]
                         " ")

                   )] ]]])]
          (core/scripts)])))
