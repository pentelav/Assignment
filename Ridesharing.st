Object subclass: Ride [
    | rideID pickup dropoff distance |

    Ride class >> newWithID: id pickup: p dropoff: d distance: dist [
        ^ self new
            initializeWithID: id pickup: p dropoff: d distance: dist
    ]

    initializeWithID: id pickup: p dropoff: d distance: dist [
        rideID := id.
        pickup := p.
        dropoff := d.
        distance := dist.
    ]

    fare [
        ^ 0
    ]

    details [
        Transcript show: 'Ride ID: ', rideID; cr.
        Transcript show: 'Pickup: ', pickup; cr.
        Transcript show: 'Dropoff: ', dropoff; cr.
        Transcript show: 'Distance: ', distance printString, ' miles'; cr.
        Transcript show: 'Fare: $', (self fare) printString; cr.
    ]
]

Ride subclass: StandardRide [
    fare [
        ^ 2.5 + (1.2 * distance)
    ]
]

Ride subclass: PremiumRide [
    fare [
        ^ 5.0 + (2.0 * distance)
    ]
]

Object subclass: Driver [
    | driverID name rating rides |

    Driver class >> newWithID: id name: n rating: r [
        ^ self new initializeWithID: id name: n rating: r
    ]

    initializeWithID: id name: n rating: r [
        driverID := id.
        name := n.
        rating := r.
        rides := OrderedCollection new.
    ]

    addRide: ride [
        rides add: ride.
    ]

    info [
        Transcript show: 'Driver ID: ', driverID; cr.
        Transcript show: 'Name: ', name; cr.
        Transcript show: 'Rating: ', rating printString; cr.
        Transcript show: 'Total Rides: ', rides size printString; cr.
    ]
]

Object subclass: Rider [
    | riderID name history |

    Rider class >> newWithID: id name: n [
        ^ self new initializeWithID: id name: n
    ]

    initializeWithID: id name: n [
        riderID := id.
        name := n.
        history := OrderedCollection new.
    ]

    requestRide: ride [
        history add: ride.
    ]

    viewRides [
        Transcript show: 'Rider ID: ', riderID; cr.
        Transcript show: 'Name: ', name; cr.
        Transcript show: 'Ride History:'; cr.
        history do: [:r | r details. Transcript show: '-------------------'; cr].
    ]
]

"--- Example Execution ---"

| driver rider ride1 ride2 |
ride1 := StandardRide newWithID: 'R201' pickup: 'Park Avenue' dropoff: 'Library' distance: 6.
ride2 := PremiumRide newWithID: 'R202' pickup: 'Airport' dropoff: 'Hotel' distance: 8.

driver := Driver newWithID: 'D500' name: 'Grace' rating: 4.9.
driver addRide: ride1.
driver addRide: ride2.

rider := Rider newWithID: 'U600' name: 'Emma'.
rider requestRide: ride1.
rider requestRide: ride2.

driver info.
Transcript cr.
rider viewRides.
