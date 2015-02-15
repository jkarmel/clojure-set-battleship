(ns clojure-set-battleship.core-test
  (:require [clojure.test :refer :all]
            [clojure-set-battleship.core :refer :all]))

(let [small-ship-at-origin [2 {:x 0 :y 0} {:x 0 :y 1}]
      large-ship-at-origin [4 {:x 0 :y 0} {:x 0 :y 1}]]
  (deftest test-ship->points
    (testing "a ship at 0 0"
      (is (= (ship->points 3 {:x 0 :y 0} {:x 1 :y 0})
            #{{:x 0 :y 0} {:x 1 :y 0} {:x 2 :y 0}}))))

  (deftest test-ship-placements=>points->ship-id
    (testing "place"
      (is (= (ship-placements=>points->ship-id [small-ship-at-origin])
            {{:x 0 :y 0} 0 {:x 0 :y 1} 0}))))

  (deftest test-collisions?
    (testing "placing overlapping ships"
      (is (collisions? [small-ship-at-origin large-ship-at-origin]))))

  (deftest test-hit?
    (testing "hitting a ship"
      (is (hit? {:x 0 :y 0} [small-ship-at-origin]))
      (is (hit? {:x 5 :y 0} [small-ship-at-origin])))))
