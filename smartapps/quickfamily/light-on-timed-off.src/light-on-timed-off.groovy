/**
 *  Light On Timed Off
 *
 *  Copyright 2017 Andrew Quick
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
    name: "Light On Timed Off",
    namespace: "quickfamily",
    author: "Andrew Quick",
    description: "Uses a minimote button to turn on a light. The light then turns off after a timed interval.",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Timed Light Control") {
		// Turns off light after X minutes unless already on
        // Triggered by motion sensor
        input "minimote", "capability.button", title: "What button?", multiple: false
        input "minutes", "number", range: "1..*", title: "Minutes", required: true, defaultValue: 5
        input "lamp1","capability.switch",title: "lamps",required: true, multiple: true
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
    log.debug "subscribe"
    subscribe(minimote, "button", buttonPushedHandler)
}

def buttonPushedHandler(evt) {
		def buttonNumber = evt.data
		def value = evt.value
		log.debug "buttonEvent: $evt.name = $evt.value ($evt.data)"
		log.debug "button: $buttonNumber, value: $value"
			switch(buttonNumber) {
				case ~/.*1.*/:
		        	log.debug "number 1 pushed"
					break
				case ~/.*2.*/:
		        	log.debug "number 2 pushed"
					break
				case ~/.*3.*/:
		        	log.debug "number 3 pushed"
					break
				case ~/.*4.*/:
		        	log.debug "number 4 pushed"
			        lamp1.on()
			        runIn(minutes*60, turnOff)
					break
			}
}
def turnOff() {
	lamp1.off()
}
