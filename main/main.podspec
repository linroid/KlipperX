Pod::Spec.new do |spec|
    spec.name                     = 'main'
    spec.version                  = '1.0-SNAPSHOT'
    spec.homepage                 = 'https://github.com/linroid/KlipperX'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'KotlinX Compose Multiplatform Shared Module'
    spec.vendored_frameworks      = 'build/cocoapods/framework/main.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '15.2'
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':main',
        'PRODUCT_MODULE_NAME' => 'main',
    }
                
    spec.script_phases = [
        {
            :name => 'Build main',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
                
end