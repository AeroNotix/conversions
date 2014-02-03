(ns com.aerokode.conversions.uuid
  (:require [clojure.data.codec.base64 :as b64]))


(defn- uuid-to-byte-array [uuid]
  (let [msb (.getMostSignificantBits uuid)
        lsb (.getLeastSignificantBits uuid)
        bb (java.nio.ByteBuffer/allocate 16)]
    (.putLong bb msb)
    (.putLong bb lsb)
    (.array bb)))

(defn uuid-to-base64 [uuid]
  (let [ba (uuid-to-byte-array uuid)]
    (b64/encode ba)))
