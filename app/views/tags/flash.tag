#{if flash.error}
	<div class="alert-message error">
		<p>&{flash.error}</p>
	</div>
#{/if}
#{if flash.success}
	<div class="alert-message info">
		<p>&{flash.success}</p>
	</div>
#{/if}
				