(ns ^{:doc "Temp file utilities for testing"}
  ephemera.fs
  (:require [clojure.java.io :as io]))

(defn ^java.io.File create-temp-root!
  []
  (doto (.toFile (java.nio.file.Files/createTempDirectory "" (into-array java.nio.file.attribute.FileAttribute [])))
    (.deleteOnExit)))

(defn ensure-directory!
 [^java.io.File file]
 (when (and (.exists file)
            (not (.isDirectory file)))
   (.delete file))
 (.mkdir file))

(defprotocol FSContent
  (write-fs [self path]))

(extend-type String
  FSContent
  (write-fs [self path]
    (spit (io/file path) self)))

(extend-type clojure.lang.APersistentMap
  FSContent
  (write-fs [self path]
    (doseq [[k v] self]
      (ensure-directory! path)
      (let [file (io/file path (name k))]
        (write-fs v file)))))

(defn write-tree!
  "Create temporary directory with contents defined by `contents-map`. 
  
  `contents-map` is nested map where keys are file or directory names
  and values are contents (generally strings or nested content maps 
  but you can extend the FSContent protocol to enable others)."
  [contents-map]
  {:post [#(.isDirectory %)]}
  (let [root (create-temp-root!)]
    (doseq [[k v] contents-map]
      (write-fs v (io/file root (name k))))
    root))
