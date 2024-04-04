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

class BundleHolder {
    Map<String, List<ProjectBundle>> bundles = [:]

    void addBundle(String projectName, ProjectBundle bundle) {
        if (!bundles.containsKey(projectName)) {
            bundles[projectName] = []
        }
        bundles[projectName].add(bundle)
    }

    void init() {
        addBundle("Van_1", new ProjectBundle("//vehicle1", "0.0.9.0"))
        addBundle("Van_1", new ProjectBundle("//vehicle2", "0.0.9.0"))
        
        addBundle("Van_2", new ProjectBundle("//vehicle1", "0.2.0.0"))
        
        addBundle("Van_3", new ProjectBundle("//vehicle1", "0.13.0.2"))
        addBundle("Van_3", new ProjectBundle("//vehicle2", "0.5.0.1"))
        addBundle("Van_3", new ProjectBundle("//vehicle3", "0.6.0.1"))
        
        addBundle("Van_4", new ProjectBundle("//vehicle1", "head"))
        addBundle("Van_4", new ProjectBundle("//vehicle2", "head"))
        addBundle("Van_4", new ProjectBundle("//vehicle3", "head"))
        addBundle("Van_4", new ProjectBundle("//vehicle4", "head"))
        addBundle("Van_4", new ProjectBundle("//vehicle5", "head"))
        
        addBundle("Van_5", new ProjectBundle("//vehicle1", "0.22.0.5"))
        addBundle("Van_5", new ProjectBundle("//vehicle2", "0.22.6.0"))
    }


    @Override
    String toString() {
        bundles.collect { projectName, bundleList ->
            def bundleStrings = bundleList.collect { bundle ->
                "${bundle.toString()}"
            }.join(',\n    ')
            
            "\"${projectName}\": [\n    ${bundleStrings}\n]"
        }.join(",\n") 
    }

}



return {
    new BundleHolder()
}