#version 330

const int MAX_DIFFUSE_LIGHT_SOURCES = 2; //Maximum number of diffuse light sources that are supported.

layout(binding = 0) uniform sampler2D texture_sampler;
layout(binding = 1) uniform sampler2D overlay_sampler;
layout(binding = 2) uniform sampler2D lightmap_sampler;

uniform vec2 lightMapTextureCoords;
uniform vec2 overlayTextureCoords;
uniform vec3 ambientLightColor;
uniform vec3 diffuseLightColors[MAX_DIFFUSE_LIGHT_SOURCES];
uniform vec3 diffuseLightLocations[MAX_DIFFUSE_LIGHT_SOURCES];

in vec2 pass_textureCoords;
in vec3 pass_vertex;
flat in vec3 pass_normal;

out vec4 out_fragmentColor;

float getDiffusionAmount(vec3 lightPos, vec3 norm) {
    vec3 normalizedLight = normalize(lightPos);
    vec3 normalizedNormal = normalize(norm);
    float normalToLightAngle = dot(normalizedNormal, normalizedLight);
    return clamp(normalToLightAngle, 0.0, 1.0);
}

void main() {
    vec4 textureColor = texture(texture_sampler, outTexCoord);
    vec4 overlayColor = texture(overlay_sampler, overlayTextureCoords);
    vec4 lightMapColor = texture(lightmap_sampler, lightMapTextureCoords);
    vec3 combinedColor = vec3(0,0,0);

    combinedColor += ambientLightColor;

    for(int i = 0; i < MAX_DIFFUSE_LIGHT_SOURCES; i++)
    {
        float diffusionAmount = getDiffusionAmount(diffuseLightLocations[i], pass_normal);
        vec3 diffuseColor = diffusionAmount * diffuseLightColors[i];
        combinedColor += diffuseColor;
    }

    vec3 litColor = combinedColor * vec3(lightMapColor) * vec3(textureColor);
    vec3 overlayedColor = vec3(overlayedColor) * litColor;

    out_fragmentColor = vec4(overlayed, texColor.w);
    gl_FragColor = out_fragmentColor;
}