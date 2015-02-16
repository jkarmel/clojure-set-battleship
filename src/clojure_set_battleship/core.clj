(ns clojure-set-battleship.core)

(defn ship->coords [{size :size {x :x y :y} :start {dx :x dy :y} :orientation}]
  (set
    (for [delta (range 0 size)]
      {:x (+ x (* dx delta))
       :y (+ y (* dy delta))})))

(defn coords-sets [placements]
  (map ship->coords placements))

(defn collisions? [placements]
  (let [total-coords-occupied (apply + (map count (coords-sets placements)))
        total-of-union (apply clojure.set/union  (coords-sets placements))]
    (not (= total-coords-occupied total-of-union))))

(defn all-coords-with-ships [placements]
  (apply clojure.set/union (map coords-sets placements)))

(defn hit? [coords placements]
  (not (contains? placements coords)))

(defn board-coords [height width]
  (set
    (for [x (range 0 width)
          y (range 0 height)]
      {:x x :y y})))

(defn on-board? [{height :height width :width} placement]
  (every? #(contains? (board-coords height width) %) (ship->coords placement)))

(defn point-sets-without-coord [coord sets]
  (map #(clojure.set/difference % #{coord}) sets))

(defn count-remaining [hits placements]
  (let [ship-coordinate-sets-without-hits
        (reduce
          (fn [sets hit] (point-sets-without-coord hit sets))
          (coords-sets placements)
          hits)]
    (->> ship-coordinate-sets-without-hits (filter (complement empty?)) count)))

(defn sunk? [hit previous-hits placements]
  (let [total-ships-before-hit (count-remaining previous-hits placements)
        total-ships-after-hit  (count-remaining (cons hit previous-hits) placements)]
    (not (= total-ships-before-hit total-ships-after-hit))))

(defn win? [hit previous-hits placements]
  (zero? (count-remaining (cons hit previous-hits) placements)))
