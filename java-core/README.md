# Hytale: Miner Echo Java Core

This directory contains the active Java implementation of Miner Echo, including the plugin source, asset pack, build script and packaged output.

## Contents

- `src/main/` — main Java source code
- `assetpack/` — custom game assets used by the plugin
- `build.sh` — local build and deployment script
- `out/` — compiled classes / output
- `MinerEchoPlugin.jar` — packaged plugin artifact

## Build

From this directory run:

```bash
./build.sh
```

The script compiles the project, creates the plugin JAR and copies the build output into the configured local Hytale server `mods` directory.

## Notes

This setup is currently configured for the local development environment used in the project.

If you want to run it elsewhere, adjust the paths inside `build.sh`.
