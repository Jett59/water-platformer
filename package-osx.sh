set -e
set +x

#export MAVEN_OPTS=-"Xmx3072m -XX:MaxPermSize=512m -XX:+CMSClassUnloadingEnabled -XX:-UseGCOverheadLimit"
mvn clean package

jpackage \
      --name water-platformer \
      -d ./water-platformer-game/target/app \
      --type pkg \
      --input ./water-platformer-game/target/release-directory \
      --app-version 1.0 \
      --license-file ./LICENSE \
      --copyright "2022 Jett Thompson" \
      --vendor "Jett Thompson" \
      --main-jar water-platformer.jar \
      --module-path ./water-platformer-game/target/release-directory/lib \
      --verbose