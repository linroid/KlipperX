//
//  AppDelegate.swift
//  KlipperX
//
//  Created by Lin Zhang on 2/7/23.
//

import UIKit
import app

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        window = UIWindow(frame: UIScreen.main.bounds)
                let mainViewController = MainKt.rootViewController()
                window?.rootViewController = mainViewController
                window?.makeKeyAndVisible()
        return true
    }

}

