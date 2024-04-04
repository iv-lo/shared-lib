package org.ucs

class ProjectBundle {
    String project
    String version

    ProjectBundle(String project, String version) {
        this.project = project
        this.version = version
    }

    @Override
    String toString() {
        return "\"${project}\": \"${version}\""
    }
}