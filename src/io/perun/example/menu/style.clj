(ns io.perun.example.menu.style)
(defn menu
  [feature-map list-of-maps]
  (loop [list-of-maps list-of-maps
         feature-map (reverse (into () feature-map))]
    (if (not-empty feature-map)
      (recur
         (map
        #(if (= (:key %) (val (peek feature-map)))
           (merge % {(key (peek feature-map)) true})
           %)
        list-of-maps)
        (pop feature-map))
      list-of-maps)))

    
