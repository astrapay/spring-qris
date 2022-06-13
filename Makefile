clean-logs:
	@rm -f -r ./logs
generate-test-sources: clean-logs
	mvn clean generate-test-sources

clean-install: clean-logs
	mvn clean install -DskipTests=true

dependency-check:
	mvn dependency-check:check -DfailBuildOnCVSS=6
