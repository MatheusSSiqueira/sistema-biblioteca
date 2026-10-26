$ErrorActionPreference = "Stop"

# Forces UTF-8 in Windows terminal to prevent mojibake on accents
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

if (!(Test-Path -Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}

$javaFiles = Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }

javac -encoding UTF-8 -d bin $javaFiles

if ($LASTEXITCODE -eq 0) {
    java -Dfile.encoding=UTF-8 -cp bin com.biblioteca.Main
}