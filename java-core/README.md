# Hytale: Miner’s Echo Java Core

This is a Java core starter for Miner’s Echo.

It contains the gameplay logic ported from the Python prototype:
- yaw to 8-direction mapping
- 5x3x2 directional scan patterns
- meaningful air cluster detection
- underground validation
- cooldown tracking
- simple demo runner

This is **not yet a Hytale plugin**. It is a portable Java core intended to be wired into a future Hytale server plugin/runtime integration.

## Run

Compile:

```bash
javac -d out $(find src/main/java -name "*.java")
```

Run:

```bash
java -cp out com.minersecho.Main
```
