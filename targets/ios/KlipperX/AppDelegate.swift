//
//  AppDelegate.swift
//  KlipperX
//
//  Created by Lin Zhang on 2/7/23.
//

import UIKit
import main

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let controller = AvoidDispose(Main_iosKt.mainViewController())
        controller.view.backgroundColor = .white
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.backgroundColor = .white
        window.rootViewController = controller
        window.makeKeyAndVisible()
        self.window = window

        return true
    }

}

