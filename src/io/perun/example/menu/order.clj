(ns io.perun.example.menu.order)
(defn order-to-list
  [order-by-key-list
   map-of-maps]
  (map #(merge {:key %} (% map-of-maps)) order-by-key-list))
