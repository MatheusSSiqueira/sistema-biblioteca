#!/usr/bin/env bash
set -euo pipefail

mkdir -p bin
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d bin @sources.txt
rm sources.txt

java -Dfile.encoding=UTF-8 -cp bin com.biblioteca.Main