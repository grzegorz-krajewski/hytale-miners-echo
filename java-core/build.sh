#!/bin/bash
set -e

cd "$(dirname "$0")"

OUT_DIR="out"
JAR_NAME="MinerEchoPlugin.jar"
SERVER_DIR="/Users/grzegorz/Projects/hytale-server"
MODS_DIR="$SERVER_DIR/mods"
HTALE_JAR="$SERVER_DIR/HytaleServer.jar"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

javac -cp "$HTALE_JAR" -d "$OUT_DIR" $(find src/main/java -name "*.java")
cp src/main/resources/manifest.json "$OUT_DIR"/
jar cf "$JAR_NAME" -C "$OUT_DIR" .

cp "$JAR_NAME" "$MODS_DIR"/"$JAR_NAME"

echo "Built and copied to $MODS_DIR/$JAR_NAME"
