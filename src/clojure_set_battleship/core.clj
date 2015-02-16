(ns clojure-set-battleship.core)

(defn ship->points [size {x :x y :y} {dx :x dy :y :as thing}]
  (set
    (for [delta (range 0 size)]
      {:x (+ x (* dx delta))
        :y (+ y (* dy delta))})))

(defn point-sets [placements]
  (map #(apply ship->points %) placements))

(defn collisions? [placements]
  (let [total-points-in-sets (apply + (map count (point-sets placements)))
        total-of-union (apply clojure.set/union  (point-sets placements))]
    (not (= total-points-in-sets total-of-union))))

(defn all-coords-with-ships [placements]
  (apply clojure.set/union (map point-sets placements)))

(defn hit? [coords placements]
  (not (contains? placements coords)))

(defn point-sets-without-coord [coord sets]
  (map #(clojure.set/difference % #{coord}) sets))

(defn count-remaining-ships [hits placements]
  (count
    (filter
      (complement empty?)
      (reduce
        (fn [sets hit] (point-sets-without-coord hit sets))
        (point-sets placements) hits))))

(defn sunk? [hit hits placements]
  (let [num-ships-before-hit (count-remaining-ships hits placements)
        num-ships-after-hit  (count-remaining-ships (cons hit hits) placements)]
    (not (= num-ships-before-hit num-ships-after-hit))))

(defn win? [hit hits placements]
  (zero? (count-remaining-ships (cons hit hits) placements)))
