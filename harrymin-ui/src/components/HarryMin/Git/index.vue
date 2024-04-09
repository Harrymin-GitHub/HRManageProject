<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'HarryMinGit',
  data() {
    return {
      url: 'https://github.com/Harrymin-GitHub/HRManageProject.git'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
