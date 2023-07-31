//Defining Variable styles


const styles = {
  default: [],

lansdcapeoff:[ {featureType: "landscape",

elementType: "all",
      stylers: [{ visibility: "off" }],



}],


poioff:[ {featureType: "poi",

stylers: [{ visibility: "off" }],



}],


roadsoff:[ {featureType: "road",

stylers: [{ visibility: "off" }],



}],
transitoff:[ {featureType: "transit",

stylers: [{ visibility: "off" }],



}],
wateroff:[ {featureType: "water",

stylers: [{ visibility: "off" }],



}],
/*
hide: [
  {
    featureType: "poi",
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "transit",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "water",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "road",
  
    stylers: [{ visibility: "off" }],
  },

],

*/
wateron: [
  {
    featureType: "poi",
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "transit",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "water",
  
    stylers: [{ visibility: "on" }],
  },
  {
    featureType: "road",
  
    stylers: [{ visibility: "off" }],
  },

],


poion: [
  {
    featureType: "poi",
    stylers: [{ visibility: "on" }],
  },
  {
    featureType: "transit",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "water",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "road",
  
    stylers: [{ visibility: "off" }],
  },

],

roadson: [
  {
    featureType: "poi",
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "transit",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "water",
  
    stylers: [{ visibility: "off" }],
  },
  {
    featureType: "road",
  
    stylers: [{ visibility: "on" }],
  },

],



 transiton: [
    {
      featureType: "poi",
      stylers: [{ visibility: "off" }],
    },
    {
      featureType: "transit",
    
      stylers: [{ visibility: "on" }],
    },
    {
      featureType: "water",
    
      stylers: [{ visibility: "off" }],
    },
    {
      featureType: "road",
    
      stylers: [{ visibility: "off" }],
    },

  ],
  poiroadsoff: [
    {
      featureType: "poi",
      stylers: [{ visibility: "off" }],
    },
    
    
    {
      featureType: "road",
    
      stylers: [{ visibility: "off" }],
    },

  ],




 poitransitoff: [
    {
      featureType: "poi",
      stylers: [{ visibility: "off" }],
    },
    {
      featureType: "transit",
    
      stylers: [{ visibility: "off" }],
    },
   

  ],
  poiriversoff: [
    {
      featureType: "poi",
      stylers: [{ visibility: "off" }],
    },
  
    {
      featureType: "water",
    
      stylers: [{ visibility: "off" }],
    },
  
 
  ],


  roadstransitoff: [
   
    {
      featureType: "transit",
    
      stylers: [{ visibility: "off" }],
    },
   
    {
      featureType: "road",
    
      stylers: [{ visibility: "off" }],
    },

  ],



  roadswateroff: [
   
    {
      featureType: "road",
    
      stylers: [{ visibility: "off" }],
    },
   
    {
      featureType: "water",
    
      stylers: [{ visibility: "off" }],
    },

  ],

  
  transitwateroff: [
   
    {
      featureType: "transit",
    
      stylers: [{ visibility: "off" }],
    },
   
    {
      featureType: "water",
    
      stylers: [{ visibility: "off" }],
    },

  ],
  
  poiroadson: [
    {
      featureType: "water",
      stylers: [{ visibility: "off" }],
    },
    
    
    {
      featureType: "transit",
    
      stylers: [{ visibility: "off" }],
    },

  ],




 poitransiton: [
    {
      featureType: "water",
      stylers: [{ visibility: "off" }],
    },
    {
      featureType: "road",
    
      stylers: [{ visibility: "off" }],
    },
   

  ],
  poiriverson: [
    {
      featureType: "road",
      stylers: [{ visibility: "off" }],
    },
  
    {
      featureType: "transit",
    
      stylers: [{ visibility: "off" }],
    },
  

  ],


  roadstransiton: [
   
    {
      featureType: "poi",
    
      stylers: [{ visibility: "off" }],
    },
   
    {
      featureType: "water",
    
      stylers: [{ visibility: "off" }],
    },

  ],



  roadswateron: [
   
    {
      featureType: "poi",
    
      stylers: [{ visibility: "off" }],
    },
   
    {
      featureType: "transit",
    
      stylers: [{ visibility: "off" }],
    },

  ],

  
  transitwateron: [
   
    {
      featureType: "road",
    
      stylers: [{ visibility: "off" }],
    },
   
    {
      featureType: "poi",
    
      stylers: [{ visibility: "off" }],
    },

  ],


};


var style = [{
  stylers:[{ color: "#ffffff" }]
  
}
];





var CustomMapStyles =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
  
          {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.province",
              "elementType": "geometry.fill",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "100"
                  },
                  {
                      "hue": "#ff0000"
                  },
                  {
                      "weight": "0.1"
                  }
              ]
          },
          {
              "featureType": "administrative.province",
              "elementType": "geometry.stroke",
              "stylers": [
                  {
                      "saturation": "-73"
                  }
              ]
          },
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
        {
          "featureType": "water",
          "elementType": "geometry",
          "stylers" : [{
              "color" : "#b6d9ff"
          }, {
              "lightness" : 17
          }]
        }
       
      ];

var CustomMapStyles2 =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            },
      ];
      

var CustomMapStyles3 =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            },
      ];

var CustomMapStyles4 =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];


var CustomMapStyles5 =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
  
          {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.province",
              "elementType": "geometry.fill",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "100"
                  },
                  {
                      "hue": "#ff0000"
                  },
                  {
                      "weight": "0.61"
                  }
              ]
          },
          {
              "featureType": "administrative.province",
              "elementType": "geometry.stroke",
              "stylers": [
                  {
                      "saturation": "-73"
                  }
              ]
          },
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];



var CustomMapStylesall =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];

var CustomMapStyles6 =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];



var CustomMapStyles7 =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "simplified"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "featureType": "administrative.land_parcel",
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "color": "#bdbdbd"
            }
          ]
        },
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];




var CountryBordersOnly =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];

var CountryBordersProvince =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];


var CountryProvinceNames =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];

var CountryProvinceOnly =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "off"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];

var CountryNamesOnly =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];




var CountryBordersNames =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "off"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];


var CountryBordersNamesProvince =[
    {
          "elementType": "geometry",
          "stylers": [
            {
              "color": "black"
            },
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.icon",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.fill",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
          "elementType": "labels.text.stroke",
          "stylers": [
            {
              "color": "#f5f5f5"
            },
            {
              "visibility": "off"
            }
          ]
        },
      
        
        {
              "featureType": "administrative.province",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "saturation": "-75"
                  },
                  {
                      "lightness": "-94"
                  },
                  {
                      "gamma": "0.00"
                  },
                  {
                      "weight": "1.00"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
        {
          "featureType": "administrative.neighborhood",
          "stylers": [
            {
              "visibility": "off"
            }
          ]
        },
        {
              "featureType": "administrative.country",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                  "color": "#ff0000"}
              ]
          },
          {
              "featureType": "administrative.country",
              "elementType": "labels",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
      
  
          {
              "featureType": "administrative.locality",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  },
                  {
                      "hue": "#ff0000"
                  }
              ]
          },
          {
              "featureType": "administrative.neighborhood",
              "elementType": "geometry",
              "stylers": [
                  {
                      "visibility": "on"
                  }
              ]
          },
          {
              "featureType": "landscape",
              "elementType": "all",
              "stylers": [
                  {
                      "color": "#ffffff"
                  },
                  {
                      "visibility": "on"
                  }
              ]
          },
       
        
          {
              "featureType": "water",
              "elementType": "geometry",
              "stylers" : [{
                  "color" : "#b6d9ff"
              }, {
                  "lightness" : 17
              }]
            }
      ];


    function setdefaultoptions(map){

      if ($("#default").is(":checked")) {
        $("#blank").prop('checked', false);
        $("#landscape").prop('checked', true);
           $("#water").prop('checked', true);
           $("#transit").prop('checked', true);
           $("#poi").prop('checked', true);
           $("#road").prop('checked', true);
        if (data=="BlankMap"|| data=="CustomMap"){
          map.setOptions({ styles: styles["default"] });
        
            }
        
      }
      else{
        $("#blank").prop('checked', true);
        $("#landscape").prop('checked', false);
           $("#water").prop('checked', false);
           $("#transit").prop('checked', false);
           $("#poi").prop('checked', false);
           $("#road").prop('checked', false);
           $("#mapgeography").prop('checked', false);
           $("#maplabels").prop('checked', false);
           $("#countrynames").prop('checked', false);
           $("#countryprovince").prop('checked', false);
        if (data=="BlankMap"){
   
            map.setOptions({ styles: style});}
   
        else if(data=="CustomMap"){
          map.setOptions({ styles: style});
   
          }
          
              }





    }




  





function SetPoiOptions(map){
    if ($("#poi").is(":checked")&& data=="CustomMap") {
      $("#default").prop('checked', true);
      $("#blank").prop('checked', false);
      if($("#transit").is(":checked")&& $("#water").is(":checked")&&$("#road").is(":checked")){
         map.setOptions({ styles: styles["default"] });
          }
 
         else if($("#transit").is(":checked")&& $("#road").is(":checked")){
           map.setOptions({ styles: styles["wateroff"] });
         }
         else if($("#road").is(":checked")&& $("#water").is(":checked")){
           map.setOptions({ styles: styles["transitoff"] });
         }
         else if($("#transit").is(":checked")&& $("#water").is(":checked")){
           map.setOptions({ styles: styles["roadsoff"] });
         }
        else if($("#transit").is(":checked")){
          map.setOptions({ styles: styles["poitransiton"] });
 
             }
             else if($("#water").is(":checked")){
               map.setOptions({ styles: styles["poiriverson"] });
             }
             else if($("#road").is(":checked")){
             
               map.setOptions({ styles: styles["poiroadson"] });
             }
      
      else{
      map.setOptions({ styles: styles["poion"] });
      }
      
    }
 
 //uncheck
    else{
      if(data=="CustomMap"){
 
        $("#default").prop('checked', false);
              if($("#transit").is(":checked")&& $("#water").is(":checked")&&$("#road").is(":checked")){
                map.setOptions({ styles: styles["poioff"] });
 
                  }
              else if($("#transit").is(":checked")&& $("#water").is(":checked")){
                map.setOptions({ styles: styles["poiroadsoff"] });
 
              }
           
              else if($("#transit").is(":checked")&& $("#road").is(":checked")){
                map.setOptions({ styles: styles["poiriversoff"] });
 
              }
              else if($("#road").is(":checked")&& $("#water").is(":checked")){
 
                map.setOptions({ styles: styles["poitransitoff"] });
              }
              else if($("#transit").is(":checked")){
                map.setOptions({ styles: styles["transiton"] });
 
              }
              else if($("#water").is(":checked")){
                map.setOptions({ styles: styles["wateron"] });
 
              }
              else if($("#road").is(":checked")){
                map.setOptions({ styles: styles["roadson"] });
 
              }
              else{
             
                $("#blank").prop('checked', true);
                map.setOptions({ styles: style});}
        }
      }


    }

   function SetRoadOptions(map){

if ($("#road").is(":checked")&& data=="CustomMap") {

		 $("#default").prop('checked', true);
     $("#blank").prop('checked', false);
		 if($("#transit").is(":checked")&& $("#water").is(":checked")&&$("#poi").is(":checked")){
	      map.setOptions({ styles: styles["default"] });
         }

        else if($("#transit").is(":checked")&& $("#poi").is(":checked")){
        	map.setOptions({ styles: styles["wateroff"] });
        }
        else if($("#poi").is(":checked")&& $("#water").is(":checked")){
        	map.setOptions({ styles: styles["transitoff"] });
        }
        else if($("#transit").is(":checked")&& $("#water").is(":checked")){
        	map.setOptions({ styles: styles["poioff"] });
        }
		   else if($("#transit").is(":checked")){
			   map.setOptions({ styles: styles["roadstransiton"] });

            }
            else if($("#water").is(":checked")){
            	map.setOptions({ styles: styles["roadswateron"] });
            }
            else if($("#poi").is(":checked")){
            
            	map.setOptions({ styles: styles["poiroadson"] });
            }
		 
		 else{
		 map.setOptions({ styles: styles["roadson"] });
		 }
 
	 }

//uncheck
	 else{
		 if(data=="CustomMap"){

			 $("#default").prop('checked', false);
             if($("#transit").is(":checked")&& $("#water").is(":checked")&&$("#poi").is(":checked")){
            	 map.setOptions({ styles: styles["roadsoff"] });

                 }
             else if($("#transit").is(":checked")&& $("#water").is(":checked")){
            	 map.setOptions({ styles: styles["poiroadsoff"] });

             }
          
             else if($("#transit").is(":checked")&& $("#poi").is(":checked")){
            	 map.setOptions({ styles: styles["roadswateroff"] });

             }
             else if($("#poi").is(":checked")&& $("#water").is(":checked")){

            	 map.setOptions({ styles: styles["roadstransitoff"] });
             }
             else if($("#transit").is(":checked")){
            	 map.setOptions({ styles: styles["transiton"] });

             }
             else if($("#water").is(":checked")){
            	 map.setOptions({ styles: styles["wateron"] });

             }
             else if($("#poi").is(":checked")){
            	 map.setOptions({ styles: styles["poion"] });

             }
             else{
            
              $("#blank").prop('checked', true);
              map.setOptions({ styles: style});}
			 }
		 
		 }



   }
function SetTransitOptions(map){

  if ($("#transit").is(":checked")&& data=="CustomMap") {

    $("#default").prop('checked', true);
    $("#blank").prop('checked', false);
    if($("#road").is(":checked")&& $("#water").is(":checked")&&$("#poi").is(":checked")){
       map.setOptions({ styles: styles["default"] });
       }

      else if($("#road").is(":checked")&& $("#poi").is(":checked")){
        map.setOptions({ styles: styles["wateroff"] });
      }
      else if($("#poi").is(":checked")&& $("#water").is(":checked")){
        map.setOptions({ styles: styles["roadsoff"] });
      }
      else if($("#road").is(":checked")&& $("#water").is(":checked")){
        map.setOptions({ styles: styles["poioff"] });
      }
      else if($("#road").is(":checked")){
        map.setOptions({ styles: styles["roadstransiton"] });

          }
          else if($("#water").is(":checked")){
            map.setOptions({ styles: styles["transitwateron"] });
          }
          else if($("#poi").is(":checked")){
          
            map.setOptions({ styles: styles["poitransiton"] });
          }
    
    else{
    map.setOptions({ styles: styles["transiton"] });
    }

  }
//uncheck
  else{
    if(data=="CustomMap"){

      $("#default").prop('checked', false);
            if($("#road").is(":checked")&& $("#water").is(":checked")&&$("#poi").is(":checked")){
              map.setOptions({ styles: styles["transitoff"] });

                }
            else if($("#road").is(":checked")&& $("#water").is(":checked")){
              map.setOptions({ styles: styles["poitransitoff"] });

            }
         
            else if($("#road").is(":checked")&& $("#poi").is(":checked")){
              map.setOptions({ styles: styles["transitwateroff"] });

            }
            else if($("#poi").is(":checked")&& $("#water").is(":checked")){

              map.setOptions({ styles: styles["roadstransitoff"] });
            }
            else if($("#road").is(":checked")){
              map.setOptions({ styles: styles["roadson"] });

            }
            else if($("#water").is(":checked")){
              map.setOptions({ styles: styles["wateron"] });

            }
            else if($("#poi").is(":checked")){
              map.setOptions({ styles: styles["poion"] });

            }
            else{
           
              $("#blank").prop('checked', true);
              map.setOptions({ styles: style});}
      }
    }


}



function SetWaterOptions(map){

  if ($("#water").is(":checked")&& data=="CustomMap") {

    $("#default").prop('checked', true);
    $("#blank").prop('checked', false);
    if($("#road").is(":checked")&& $("#transit").is(":checked")&&$("#poi").is(":checked")){
       map.setOptions({ styles: styles["default"] });
      }

     else if($("#road").is(":checked")&& $("#poi").is(":checked")){
       map.setOptions({ styles: styles["transitoff"] });
     }
     else if($("#poi").is(":checked")&& $("#transit").is(":checked")){
       map.setOptions({ styles: styles["roadsoff"] });
     }
     else if($("#road").is(":checked")&& $("#transit").is(":checked")){
       map.setOptions({ styles: styles["poioff"] });
     }
      else if($("#road").is(":checked")){
        map.setOptions({ styles: styles["roadswateron"] });

         }
         else if($("#transit").is(":checked")){
           map.setOptions({ styles: styles["transitwateron"] });
         }
         else if($("#poi").is(":checked")){
         
           map.setOptions({ styles: styles["poiriverson"] });
         }
    
    else{
    map.setOptions({ styles: styles["wateron"] });
    }

  }
//uncheck
  else{
    if(data=="CustomMap"){

      $("#default").prop('checked', false);
           if($("#road").is(":checked")&& $("#transit").is(":checked")&&$("#poi").is(":checked")){
             map.setOptions({ styles: styles["wateroff"] });

               }
           else if($("#road").is(":checked")&& $("#transit").is(":checked")){
             map.setOptions({ styles: styles["poiriversoff"] });

           }
        
           else if($("#road").is(":checked")&& $("#poi").is(":checked")){
             map.setOptions({ styles: styles["transitwateroff"] });

           }
           else if($("#poi").is(":checked")&& $("#transit").is(":checked")){

             map.setOptions({ styles: styles["roadswateroff"] });
           }
           else if($("#road").is(":checked")){
             map.setOptions({ styles: styles["roadson"] });

           }
           else if($("#transit").is(":checked")){
             map.setOptions({ styles: styles["transiton"] });

           }
           else if($("#poi").is(":checked")){
             map.setOptions({ styles: styles["poion"] });

           }
           else{
            $("#blank").prop('checked', true);
            map.setOptions({ styles: style});}
      }
    }

}

// Blank Map functions

function SetLightGeographyOptions(map){
  $("#blank").prop('checked', false);
  $("#default").prop('checked', false);
 $("#landscape").prop('checked', false);
 $("#water").prop('checked', false);
 $("#transit").prop('checked', false);
 $("#poi").prop('checked', false);
 $("#road").prop('checked', false);
  if ($("#mapgeography").is(":checked")) {
    

    if ($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")&&$('#countryprovince').is(":checked")) {
          map.setOptions({ styles: CustomMapStylesall });
         
            }
      else if($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")){
            map.setOptions({ styles: CustomMapStyles3 }); 
            
          }
      
      
      else if($('#countryprovince').is(":checked")&&$('#countrynames').is(":checked")){
            map.setOptions({ styles: CustomMapStyles6 }); 
            
          }
      else if($('#countryprovince').is(":checked")&&$('#maplabels').is(":checked")){
            map.setOptions({ styles: CustomMapStyles7 }); 
            
          }
      else if($('#maplabels').is(":checked")){
            map.setOptions({ styles: CustomMapStyles2 }); 
            
          }
      else if($('#countrynames').is(":checked")){
            map.setOptions({ styles: CustomMapStyles4 }); 
            
          }
      
      else if($('#countryprovince').is(":checked")){
            map.setOptions({ styles: CustomMapStyles5 }); 
            
          }
         
          else{
          map.setOptions({ styles: CustomMapStyles});}
        
      }
      
      
      
      
         else {
           
           
           if ($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")&&$('#countryprovince').is(":checked")) {
            map.setOptions({ styles: CountryBordersNamesProvince });
              }
           else  if ($('#countrynames').is(":checked")&&$('#countryprovince').is(":checked")) {
            map.setOptions({ styles: CountryProvinceNames });
              }
           else if ($('#maplabels').is(":checked")&&$('#countryprovince').is(":checked")) {
            map.setOptions({ styles: CountryBordersProvince });
              }
           else if ($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")) {
            map.setOptions({ styles: CountryBordersNames });
              }
           else if ($('#maplabels').is(":checked")) {
          map.setOptions({ styles: CountryBordersOnly });
         
            }
          else if($('#countrynames').is(":checked")){
            map.setOptions({ styles: CountryNamesOnly }); 
            
          }
          else if($('#countryprovince').is(":checked")){
            map.setOptions({ styles: CountryProvinceOnly  }); 
            
          }
          else if($('#default').is(":checked")){
            map.setOptions({ styles: styles["default"] });    
                
              }
          else{
   
   
            $("#blank").prop('checked', true);
          map.setOptions({ styles: style});}
        }


}

function SetCountryBorders(map){
 $("#blank").prop('checked', false);
$("#default").prop('checked', false);
    $("#landscape").prop('checked', false);
    $("#water").prop('checked', false);
    $("#transit").prop('checked', false);
    $("#poi").prop('checked', false);
    $("#road").prop('checked', false);
  if ($("#maplabels").is(":checked")) {
			 
    if ($('#countryprovince').is(":checked")&&$('#countrynames').is(":checked")&&$('#mapgeography').is(":checked")) {
      map.setOptions({ styles: CustomMapStylesall });
        }
    else if($('#countryprovince').is(":checked")&&$('#countrynames').is(":checked")){
        map.setOptions({ styles: CountryBordersNamesProvince }); 
        
      }
    
    else if($('#countryprovince').is(":checked")&&$('#mapgeography').is(":checked")){
        map.setOptions({ styles:  CustomMapStyles7}); 
        
      }
    
    else if( $('#mapgeography').is(":checked")){
      
      
      map.setOptions({ styles: CustomMapStyles2 }); 
      
      
    }
    
    else if($('#countrynames').is(":checked")){
      map.setOptions({ styles: CountryBordersNames}); 
      
    }
    
         else if($('#countryprovince').is(":checked")){
      
      map.setOptions({ styles: CountryBordersProvince});  
      
    }
    
    else{
      map.setOptions({ styles: CountryBordersOnly }); 
    }
   
   
    } else {
      
      if ($('#countryprovince').is(":checked")&&$('#countrynames').is(":checked")&&$('#mapgeography').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles6 });
        }
      
      else if ($('#countrynames').is(":checked")&&$('#countryprovince').is(":checked")) {
      map.setOptions({ styles: CountryProvinceNames });
        }
      
      else if ($('#mapgeography').is(":checked")&&$('#countrynames').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles4 });
        }
    
   
    else if($('#mapgeography').is(":checked")&&$('#countryprovince').is(":checked")){
        map.setOptions({ styles: CustomMapStyles5 }); 
        
      }
      else if($('#countrynames').is(":checked")){
        map.setOptions({ styles: CountryNamesOnly }); 
        
      }
      else if($('#mapgeography').is(":checked")&&$('#countryprovince').is(":checked")){
        map.setOptions({ styles: CustomMapStyles5 }); 
        
      }
      else if($('#mapgeography').is(":checked")){
        map.setOptions({ styles: CustomMapStyles }); 
        
      }
      
      else if($('#countryprovince').is(":checked")){
        map.setOptions({ styles: CountryProvinceOnly }); 
        
      }
     else if($('#default').is(":checked")){
       map.setOptions({ styles: styles["default"] });    
           
         }
      else{

        $("#blank").prop('checked', true);
      map.setOptions({ styles: style});}
     
    }



  
}
function SetCountryNames(map){
   $("#blank").prop('checked', false);
    $("#default").prop('checked', false);
    $("#landscape").prop('checked', false);
    $("#water").prop('checked', false);
    $("#transit").prop('checked', false);
    $("#poi").prop('checked', false);
    $("#road").prop('checked', false);
  if ($("#countrynames").is(":checked")) {
			 
    if ($('#maplabels').is(":checked")&&$('#mapgeography').is(":checked")&&$('#countryprovince').is(":checked")) {
      map.setOptions({ styles: CustomMapStylesall });
        }
    else if ($('#mapgeography').is(":checked")&&$('#countryprovince').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles6 });
        }
    
    else if ($('#mapgeography').is(":checked")&&$('#maplabels').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles3 });
        }
    
    else if ($('#countryprovince').is(":checked")&&$('#maplabels').is(":checked")) {
      map.setOptions({ styles:CountryBordersNamesProvince   });
        }
    else if ($('#mapgeography').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles4});
        }
    
    else if ($('#maplabels').is(":checked")) {
      map.setOptions({ styles: CountryBordersNames});
        }
    
    else if($('#countryprovince').is(":checked")){
      
      map.setOptions({ styles: CountryProvinceNames});  
      
    }
    
    
    else{
    
      map.setOptions({ styles: CountryNamesOnly}); 
     
    }
   
   
    }
  
  
  
  
  
  else {
    if ($('#maplabels').is(":checked")&&$('#mapgeography').is(":checked")&&$('#countryprovince').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles7 });
        }
    else if ($('#maplabels').is(":checked")&&$('#mapgeography').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles2 });
        }
    else if($('#mapgeography').is(":checked")&&$('#countryprovince').is(":checked")){
        map.setOptions({ styles: CustomMapStyles5 }); 
        
      }
    else if($('#maplabels').is(":checked")&&$('#countryprovince').is(":checked")){
        map.setOptions({ styles: CountryBordersProvince }); 
        
      }
    
    
      else if($('#maplabels').is(":checked")){
        
        map.setOptions({ styles: CountryBordersOnly }); 
      }
     else if($('#mapgeography').is(":checked")){
        
        map.setOptions({ styles: CustomMapStyles }); 
      }
     else if($('#countryprovince').is(":checked")){
        map.setOptions({ styles: CountryProvinceOnly }); 
        
      }
     else if($('#default').is(":checked")){
       map.setOptions({ styles: styles["default"] });    
           
         }
      else{
        $("#blank").prop('checked', true);
      map.setOptions({ styles: style});}
     
    }


  
}
function SetCountryProvice(map){
  $("#blank").prop('checked', false);
$("#default").prop('checked', false);
    $("#landscape").prop('checked', false);
    $("#water").prop('checked', false);
    $("#transit").prop('checked', false);
    $("#poi").prop('checked', false);
    $("#road").prop('checked', false);
  if ($("#countryprovince").is(":checked")) {
    if ($('#maplabels').is(":checked")&&$('#mapgeography').is(":checked")&&$('#countrynames').is(":checked")) {
      map.setOptions({ styles: CustomMapStylesall });
        }
    
    else if ($('#maplabels').is(":checked")&&$('#mapgeography').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles7 });
        }
   
    else if ($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")) {
      map.setOptions({ styles: CountryBordersNamesProvince });
        }
     else if($('#mapgeography').is(":checked")){
        
        map.setOptions({ styles: CustomMapStyles5 }); 
      }
    else if($('#maplabels').is(":checked")){
    map.setOptions({ styles: CountryBordersProvince }); 
      
    }
    else if($('#countrynames').is(":checked")){
      map.setOptions({ styles: CountryProvinceNames }); 
      
    }
   
    else{
      map.setOptions({ styles: CountryProvinceOnly}); 
    }
      
    
   
   
    }
 
  else {
    if ($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")&&$('#mapgeography').is(":checked")) {
      map.setOptions({ styles: CustomMapStyles3 });
        }
    else if ($('#maplabels').is(":checked")&&$('#countrynames').is(":checked")) {
      map.setOptions({ styles: CountryBordersNames });
        }
      else if($('#maplabels').is(":checked")){
        
        map.setOptions({ styles: CountryBordersOnly }); 
      }
     else if($('#mapgeography').is(":checked")){
        
        map.setOptions({ styles: CustomMapStyles }); 
      }
      
     else if($('#countrynames').is(":checked")){
         console.log("here");
       map.setOptions({ styles: CountryNamesOnly}); 
        
      }
     else if($('#default').is(":checked")){
       map.setOptions({ styles: styles["default"] });    
           
         }
      else{
        $("#blank").prop('checked', true);
      map.setOptions({ styles: style});}
     
    }

  
}



function setblankoptions(map){

  if ($("#blank").is(":checked")) {
      $("#default").prop('checked', false);
       $("#landscape").prop('checked', false);
       $("#water").prop('checked', false);
       $("#transit").prop('checked', false);
       $("#poi").prop('checked', false);
       $("#road").prop('checked', false);
      $("#mapgeography").prop('checked', false);
      $("#maplabels").prop('checked', false);
      $("#countrynames").prop('checked', false);
      $("#countryprovince").prop('checked', false);
    
    if (data=="BlankMap"|| data=="CustomMap"){
      map.setOptions({ styles: style});}
    
        }
    

  else{

 
    if (data=="BlankMap"){

        map.setOptions({ styles: style});}

    else if(data=="CustomMap"){
      $("#default").prop('checked', true);
      $("#landscape").prop('checked', true);
      $("#water").prop('checked', true);
      $("#transit").prop('checked', true);
      $("#poi").prop('checked', true);
      $("#road").prop('checked', true);
      map.setOptions({ styles: styles["default"] });

      }
      
          }



        }


