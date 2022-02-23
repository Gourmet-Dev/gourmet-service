#!/usr/bin/env bash

DETEKT_CURRENT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
DETEKT_TARGET_DIR="$DETEKT_CURRENT_DIR/../.git/hooks"
DETEKT_TARGET_FILE="$DETEKT_TARGET_DIR/pre-commit"
DETEKT_HOOK_SCRIPT="$DETEKT_CURRENT_DIR/.detekt_hook_script.orig"

if [[ ! -d "$DETEKT_TARGET_DIR" ]]
then
    mkdir -p "$DETEKT_SCRIPT_DIR"
fi

if [[ -f "$DETEKT_TARGET_FILE" ]]
then
    echo "*************************************************"
    echo "            Detekt integration failed            "
    echo "*************************************************"
    echo "Reason: GIT pre-commit hook is already installed!"
    exit 1
fi

cat "$DETEKT_HOOK_SCRIPT" > "$DETEKT_TARGET_FILE"
chmod +x "$DETEKT_TARGET_FILE"

echo "************************************************"
echo "                Detekt installed                "
echo "************************************************"
echo "Install path: $(ls "$DETEKT_TARGET_FILE")"
