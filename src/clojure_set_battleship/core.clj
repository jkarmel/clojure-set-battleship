(ns clojure-set-battleship.core)

(defn ship->points [size {x :x y :y} {dx :x dy :y}]
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

(defn board-coords [height width]
  (set
    (for [x (range 0 width)
          y (range 0 height)]
      {:x x :y y})))

(defn on-board? [{height :height width :width} placement]
  (every? #(contains? (board-coords height width) %) (apply ship->points placement)))

(defn point-sets-without-coord [coord sets]
  (map #(clojure.set/difference % #{coord}) sets))

(defn count-remaining [hits placements]
  (count
    (filter
      (complement empty?)
      (reduce
        (fn [sets hit] (point-sets-without-coord hit sets))
        (point-sets placements)
        hits))))

(defn sunk? [hit hits placements]
  (let [num-ships-before-hit (count-remaining hits placements)
        num-ships-after-hit  (count-remaining (cons hit hits) placements)]
    (not (= num-ships-before-hit num-ships-after-hit))))

(defn win? [hit hits placements]
  (zero? (count-remaining (cons hit hits) placements)))
