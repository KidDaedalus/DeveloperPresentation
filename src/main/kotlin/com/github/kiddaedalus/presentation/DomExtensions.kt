package com.github.kiddaedalus.presentation

import org.w3c.dom.Element
import org.w3c.dom.events.MouseEvent

// Add convenient extension functions to document-object-model (DOM) related functionality
fun Element.onClick(callback: MouseEvent.()->Unit) {
    this.addEventListener("click",
            { event ->
                event as MouseEvent
                event.callback()
            })
}
