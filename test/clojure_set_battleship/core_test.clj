(ns clojure-set-battleship.core-test
  (:require [clojure.test :refer :all]
            [clojure-set-battleship.core :refer :all]))

(let [origin {:x 0 :y 0}
      small-ship-at-origin [2 origin {:x 0 :y 1}]
      large-ship-at-origin [4 origin {:x 0 :y 1}]]

  (deftest test-ship->points
    (is (= (ship->points 3 {:x 0 :y 0} {:x 1 :y 0})
          #{{:x 0 :y 0} {:x 1 :y 0} {:x 2 :y 0}})))

  (deftest test-on-board?
    (is (not (on-board? {:height 10 :width 10} [3 {:x -1 :y 0} {:x 1 :y 0}]))))

  (deftest test-collisions?
    (testing "placing overlapping ships"
      (is (collisions? [small-ship-at-origin large-ship-at-origin]))))

  (deftest test-hit?
    (is (hit? {:x 0 :y 0} [small-ship-at-origin]))
    (is (hit? {:x 5 :y 0} [small-ship-at-origin])))

  (deftest test-count-remaining-ships
    (is (= (count-remaining-ships [] [small-ship-at-origin]) 1))
    (is (= (count-remaining-ships [{:x 0 :y 0} {:x 0 :y 1}] [small-ship-at-origin]) 0)))

  (deftest test-sunk?
    (is (sunk? origin [{:x 0 :y 1}] [small-ship-at-origin])))

  (deftest test-win?
    (is (win? origin [{:x 0 :y 1}] [small-ship-at-origin]))
    (is (not (win? origin [{:x 5 :y 5}] [small-ship-at-origin])))))
