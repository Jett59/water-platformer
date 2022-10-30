set -e
set +x

mvn clean package

jpackage \
      --name water-platformer \
      -d ./water-platformer-game/target/app \
      --type msi \
      --input ./water-platformer-game/target/release-directory \
      --app-version 1.0 \
      --license-file ./LICENSE \
      --copyright "2022 Jett Thompson" \
      --vendor "Jett Thompson" \
      --main-jar water-platformer.jar \
      --module-path ./water-platformer-game/target/release-directory/lib \
--win-shortcut \
--win-menu \
--win-dir-chooser \
--win-upgrade-uuid "dccbc3c2-4ffb-43a2-8db1-7cad807036b4" \
      --verbose