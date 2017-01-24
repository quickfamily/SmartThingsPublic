/**
 *  help
 *
 *  Copyright 2016 Andrew Quick
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Modified Turn Off Timer",
    namespace: "quickfamilyusa",
    author: "Andrew Quick",
    description: "turns light off after X minutes unless light already on. Uses motion as trigger",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Timed Light Control") {
		// Turns off light after X minutes unless already on
        // Triggered by motion sensor
        input "motion1", "capability.motionSensor", title: "What motion sensor?", multiple: false
        input "minutes", "number", range: "1..*", title: "Minutes", required: true, defaultValue: 5
        input "lamp1","capability.switch",title: "lamp",required: true, multiple: true
//        input "modes", "mode", title: "Only when mode is", multiple: true, required: false
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
    log.debug "subscribe"
    subscribe(motion1, "motion.active", motionActiveHandler)

}

// TODO: implement event handlers
def motionActiveHandler(evt) {
	def switchOn = "on" in lamp1.currentSwitch
    if(switchOn)
    	log.debug "current status is on"
    else {
    	log.debug "current status is off"
        lamp1.on()
        runIn(minutes*60, turnOff)
    }
        
}
def turnOff() {
	lamp1.off()
}
//private getModeOk() {
//	def result = !modes || modes.contains(location.mode)
//	log.debug "modeOk = $result"
//	return result
//}
