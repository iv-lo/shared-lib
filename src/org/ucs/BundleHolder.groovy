package org.ucs

import groovy.json.JsonSlurper

class BundleHolder {
    protected Map<String, List<Project>> _bundles = [:]
    String initBundles = """
            {
        "Van_1": [
            {"//vehicle1": "0.0.9.0"},
            {"//vehicle2": "0.0.9.0"}
        ],
        "Van_2": [
            {"//vehicle1": "0.2.0.0"}
        ],
        "Van_3": [
            {"//vehicle1": "0.13.0.2"},
            {"//vehicle2": "0.5.0.1"},
            {"//vehicle3": "0.6.0.1"}
        ],
        "Van_4": [
            {"//vehicle1": "head"},
            {"//vehicle2": "head"},
            {"//vehicle3": "head"},
            {"//vehicle4": "head"},
            {"//vehicle5": "head"}
        ],
        "Van_5": [
            {"//vehicle1": "0.22.0.5"},
            {"//vehicle2": "0.22.6.0"}
        ]
        }
    """

    static BundleHolder createInitializedInstance() {
        BundleHolder holder = new BundleHolder()
        holder.initializeFromString(holder.initBundles)
        return holder
    }

    void addBundle(String projectName, Project bundle) {
        if (!bundles.containsKey(projectName)) {
            _bundles[projectName] = []
        }
        _bundles[projectName].add(bundle)
    }

    String toJsonString() {
        def bundleStrings = _bundles.collect { projectName, bundleList ->
            def bundleStrings = bundleList.collect { bundle ->
                "${bundle.toJsonString()}"
            }.join(',\n    ')
            
            "\"${projectName}\": [\n    ${bundleStrings}\n]"
        }.join(",\n") 

        return "{\n${bundleStrings}\n}"
    }

    void initializeFromString(String bundlesProjectsText) {
        _bundles.clear()
        def jsonSlurper = new JsonSlurper()
        def projectsMap = jsonSlurper.parseText(bundlesProjectsText)

        projectsMap.each { projectName, bundlesList ->
            bundlesList.each { Map bundleInfo ->
                bundleInfo.each { projectPath, version ->
                    this.addBundle(projectName, new Project(projectPath, version))
                }
            }
        }
    }

    List<Map<String, String>> getBundleProjects(String key) {
        return bundles.getOrDefault(key, [])
        // def projects = bundles.getOrDefault(key, [])
        // String projectText = projects.collect { bundle ->
        //     "${bundle.toWorkspaceCfgLink()}"
        // }.join("\n")
        // return projectText
    }

}


class Project {
    String project
    String version
    String commit

    Project(String project, String version) {
        this.project = project
        this.version = version
    }

    String toJsonString() {
        return "{\"${project}\": \"${version}\"}"
    }

    String toWorkspaceCfgLink(){
        return "LINK ${project} ${version}"
    }

    String getName(){
        return project.split('/')[-1]
    }
}