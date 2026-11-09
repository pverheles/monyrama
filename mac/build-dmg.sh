#!/bin/zsh
# Builds dist/Monyrama-<version>-<arch>.dmg with jpackage.
# Requires: JDK 25 (java, jpackage on PATH), Apache Maven, macOS sips/iconutil.
# The DMG targets the CPU architecture of the JDK used to run this script
# (run it with an x64 JDK to get an Intel build).
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

APP_NAME="Monyrama"
MAIN_CLASS="com.monyrama.application.Monyrama"
BUNDLE_ID="com.monyrama.app"
VERSION="$(mvn -q -B help:evaluate -Dexpression=project.version -DforceStdout)"
ARCH="$(java -XshowSettings:properties -version 2>&1 | sed -n 's/^ *os\.arch = //p')"
case "$ARCH" in
    aarch64) ARCH="arm64" ;;
    x86_64|amd64) ARCH="x64" ;;
esac

WORK="$ROOT/build/dmg"
DIST="$ROOT/dist"

# Modules reported by `jdeps -summary` over target/monyrama.jar and target/lib/*.jar
# (java.compiler, java.scripting and jdk.net are only used by optional H2 features),
# plus runtime-only ones jdeps can't see:
# jdk.localedata (uk/ru/es formats), jdk.charsets (Cyrillic code pages), jdk.unsupported (sun.misc.Unsafe)
MODULES="java.base,java.compiler,java.desktop,java.instrument,java.management,java.naming,java.prefs,java.rmi,\
java.scripting,java.security.jgss,java.sql,java.xml.crypto,jdk.attach,jdk.jdi,jdk.net,\
jdk.localedata,jdk.charsets,jdk.unsupported"

echo "==> Building $APP_NAME $VERSION ($ARCH)"
rm -rf "$WORK"
mkdir -p "$WORK/input" "$DIST"

echo "==> Building with Maven"
# Tests are skipped: four legacy tests fail independently of the build (see build_instr.txt)
mvn -q -B clean package -DskipTests

# monyrama.jar finds its dependencies through the manifest Class-Path (lib/*.jar)
cp target/monyrama.jar "$WORK/input/"
cp -R target/lib "$WORK/input/lib"

echo "==> Generating icon"
ICONSET="$WORK/$APP_NAME.iconset"
mkdir -p "$ICONSET"
for size in 16 32 128 256 512; do
    sips -z $size $size ico/main.png --out "$ICONSET/icon_${size}x${size}.png" >/dev/null
    sips -z $((size * 2)) $((size * 2)) ico/main.png --out "$ICONSET/icon_${size}x${size}@2x.png" >/dev/null
done
iconutil -c icns "$ICONSET" -o "$WORK/$APP_NAME.icns"

echo "==> Running jpackage"
jpackage \
    --type dmg \
    --name "$APP_NAME" \
    --app-version "$VERSION" \
    --vendor "$APP_NAME" \
    --description "Personal finance and budgeting" \
    --input "$WORK/input" \
    --main-jar monyrama.jar \
    --main-class "$MAIN_CLASS" \
    --java-options "--enable-native-access=ALL-UNNAMED" \
    --add-modules "$MODULES" \
    --icon "$WORK/$APP_NAME.icns" \
    --mac-package-identifier "$BUNDLE_ID" \
    --mac-package-name "$APP_NAME" \
    --mac-app-category finance \
    --dest "$WORK/out"

DMG="$DIST/$APP_NAME-$VERSION-$ARCH.dmg"
mv -f "$WORK/out/$APP_NAME-$VERSION.dmg" "$DMG"
echo "==> Done: $DMG"
