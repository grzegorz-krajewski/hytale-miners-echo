#!/bin/bash
set -e

cd "$(dirname "$0")"

OUT_DIR="out"
JAR_NAME="MinerEchoPlugin.jar"
SERVER_DIR="/Users/grzegorz/Projects/hytale-server"
MODS_DIR="$SERVER_DIR/mods"
HTALE_JAR="$SERVER_DIR/HytaleServer.jar"

# asset pack lokalny w projekcie
ASSETPACK_SRC_DIR="assetpack"

# gdzie skopiować asset pack do testów przy serwerze
ASSETPACK_DST_DIR="$MODS_DIR/MinerEchoPlugin"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Compiling Java..."
javac -cp "$HTALE_JAR" -d "$OUT_DIR" $(find src/main/java -name "*.java")

echo "Copying manifest..."
cp src/main/resources/manifest.json "$OUT_DIR"/

echo "Creating JAR..."
jar cf "$JAR_NAME" -C "$OUT_DIR" .

echo "Copying JAR to mods..."
cp "$JAR_NAME" "$MODS_DIR"/"$JAR_NAME"

if [ -d "$ASSETPACK_SRC_DIR" ]; then
  echo "Copying asset pack from: $ASSETPACK_SRC_DIR"
  echo "Asset pack source contents:"
  find "$ASSETPACK_SRC_DIR" -maxdepth 3 | sort

  rm -rf "$ASSETPACK_DST_DIR"
  mkdir -p "$ASSETPACK_DST_DIR"
  cp -R "$ASSETPACK_SRC_DIR"/. "$ASSETPACK_DST_DIR"/

  echo "Asset pack copied to $ASSETPACK_DST_DIR"
  echo "Asset pack destination contents:"
  find "$ASSETPACK_DST_DIR" -maxdepth 3 | sort
else
  echo "No assetpack directory found, skipping asset pack copy."
fi

echo "Built and copied to $MODS_DIR/$JAR_NAME"