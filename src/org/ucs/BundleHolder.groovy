package org.ucs

import groovy.json.JsonSlurper

class BundleHolder {
    Map<String, List<ProjectBundle>> bundles = [:]

    static BundleHolder createInitializedInstance() {
        BundleHolder holder = new BundleHolder()
        holder.addBundle("Van_1", new ProjectBundle("//vehicle1", "0.0.9.0"))
        holder.addBundle("Van_1", new ProjectBundle("//vehicle2", "0.0.9.0"))
        holder.addBundle("Van_2", new ProjectBundle("//vehicle1", "0.2.0.0"))
        holder.addBundle("Van_3", new ProjectBundle("//vehicle1", "0.13.0.2"))
        holder.addBundle("Van_3", new ProjectBundle("//vehicle2", "0.5.0.1"))
        holder.addBundle("Van_3", new ProjectBundle("//vehicle3", "0.6.0.1"))
        holder.addBundle("Van_4", new ProjectBundle("//vehicle1", "head"))
        holder.addBundle("Van_4", new ProjectBundle("//vehicle2", "head"))
        holder.addBundle("Van_4", new ProjectBundle("//vehicle3", "head"))
        holder.addBundle("Van_4", new ProjectBundle("//vehicle4", "head"))
        holder.addBundle("Van_4", new ProjectBundle("//vehicle5", "head"))
        holder.addBundle("Van_5", new ProjectBundle("//vehicle1", "0.22.0.5"))
        holder.addBundle("Van_5", new ProjectBundle("//vehicle2", "0.22.6.0"))
        return holder
    }

    void addBundle(String projectName, ProjectBundle bundle) {
        if (!bundles.containsKey(projectName)) {
            bundles[projectName] = []
        }
        bundles[projectName].add(bundle)
    }

    @Override
    String toString() {
        def bundleStrings = bundles.collect { projectName, bundleList ->
            def bundleStrings = bundleList.collect { bundle ->
                "${bundle.toString()}"
            }.join(',\n    ')
            
            "\"${projectName}\": [\n    ${bundleStrings}\n]"
        }.join(",\n") 

        return "{\n${bundleStrings}\n}"
    }

    void initializeFromMap(String bundlesProjectsText) {
        bundles.clear()
        def jsonSlurper = new JsonSlurper()
        def projectsMap = jsonSlurper.parseText(bundlesProjectsText)
        // println(projectsMap)
        // def evaluatedMap = evaluate(bundlesProjectsText)
        // evaluatedMap.each { projectName, bundlesMap ->
        //     bundlesMap.each { projectPath, version ->
        //         this.addBundle(projectName, new ProjectBundle(projectPath, version))
        //     }
        // }
    }

}


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