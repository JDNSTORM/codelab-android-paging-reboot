//
//  iosAppApp.swift
//  iosApp
//
//  Created by James Dave Navor on 6/24/25.
//

import SwiftUI
import composeApp

@main
struct iosAppApp: App {
    init () {
        AppInitializerKt.initializeApp()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
